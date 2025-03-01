package repositories

import models.{Content, Contents}
import slick.jdbc.PostgresProfile.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ContentRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
                                  implicit val ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val contents = TableQuery[Contents]

  // Fetch content associated with a specific influencer
  def getByInfluencerId(influencerId: Int): Future[Seq[Content]] = {
    db.run(contents.filter(_.influencerId === influencerId).result)
  }

  // Calculate average metrics (likes, shares, comments) for content of a specific influencer
  def calculateAverageMetrics(influencerId: Int): Future[(Int, Int, Int)] = {
    db.run {
      contents.filter(_.influencerId === influencerId).result.map { posts =>
        val totalLikes = posts.map(_.likes).sum
        val totalShares = posts.map(_.shares).sum
        val totalComments = posts.map(_.comments).sum

        val count = posts.size
        if (count > 0) {
          (
            (totalLikes.toDouble / count).toInt,
            (totalShares.toDouble / count).toInt,
            (totalComments.toDouble / count).toInt,
          )
        } else {
          (0, 0, 0) // If there are no posts, return 0 for all
        }
      }
    }
  }
}
