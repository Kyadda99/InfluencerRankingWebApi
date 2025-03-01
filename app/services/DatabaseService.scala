package services

import models._

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLServerProfile.api._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DatabaseService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  // Access the database instance from the configuration provider
  val db = dbConfigProvider.get[JdbcProfile].db

  // Table creation setup (without InfluencerCampaigns)
  val setup = DBIO.seq(
    (TableQuery[Influencers].schema ++
      TableQuery[Categories].schema ++
      TableQuery[Campaigns].schema ++
      TableQuery[Contents].schema).createIfNotExists // Remove InfluencerCampaigns
  )

  // Initialize the database
  def initialize(): Future[Unit] = {
    db.run(setup).map { _ =>
      println("Tables have been created or already exist.")
    }
  }
}
