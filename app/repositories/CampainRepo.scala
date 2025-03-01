package repositories

import models.{Campaigns, Content, Contents}
import slick.jdbc.PostgresProfile.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CampainRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
                            implicit val ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val contents = TableQuery[Campaigns]


  // Calculate average metrics (likes, shares, comments) for content of a specific influencer
  def calculateAverageCampaignEngagment(influencerId: Int): Future[(Double,Int)] = {
    db.run {
      contents.filter(_.influencerId === influencerId).result.map { campaigns =>
        val totalOpens = campaigns.map(_.amountOfOpens).sum
        val totalInteractions = campaigns.map(_.amountOfInteractions).sum
        val campaignsCount = campaigns.size

        val count = campaigns.size
        if (count > 0) {
          (
            totalInteractions.toDouble / totalOpens.toDouble,
            campaignsCount.toInt
          )
        } else {
          (0.0,0) // If there are no posts, return 0 for all
        }
      }
    }
  }
}
