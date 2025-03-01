package repositories

import models.{Influencer, Influencers}
import slick.jdbc.PostgresProfile.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class InfluencerRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val influencers = TableQuery[Influencers]

  def getAll: Future[Seq[Influencer]] = db.run(influencers.result)

  def getInfluencers: Future[Seq[Influencer]] = {
    db.run(influencers
      .sortBy(_.followers)
      .result)
  }


}
