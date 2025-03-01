package controllers

import repositories._

import javax.inject._
import play.api.mvc._
import repositories.InfluencerRepo
import play.api.libs.json._
import models.{Campaign, Influencer, InfluencerWithEngagment, InfluencerWithFullData, InfluencerWithMetrics}
import slick.ast.SortBy

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class InfluencerController @Inject()(cc: ControllerComponents,influencerRepo: InfluencerRepo,contentRepo: ContentRepo, campainRepo: CampainRepo)
                                    (implicit ec: ExecutionContext) extends AbstractController(cc) {

  implicit val influencerFormat = Json.format[Influencer]
  implicit val influencerWithMetricsFormat = Json.format[InfluencerWithMetrics]

//  def getAllInfluencers: Action[AnyContent] = Action.async {
//    influencerRepo.getAll.map { influencers =>
//      Ok(views.html.influencers(influencers))
//    }
//  }

  def getNano(sortBy: Option[String]): Action[AnyContent] = Action.async {
    influencerRepo.getInfluencers.flatMap { influencers =>
      // Create a future for each influencer to calculate both campaign and content metrics
      val influencerDataFutures = influencers.map { influencer =>
        // Future for campaign metrics
        val campaignMetricsFuture = campainRepo.calculateAverageCampaignEngagment(influencer.id)

        // Future for content metrics
        val contentMetricsFuture = contentRepo.calculateAverageMetrics(influencer.id)

      // Await all Futures to be resolved
        for {
          campaignMetrics <- campaignMetricsFuture
          contentMetrics <- contentMetricsFuture
        } yield {
          InfluencerWithFullData(
            influencer,
            campaignMetrics._2, // campaigns count
            contentMetrics._1, // averageLikes
            contentMetrics._2, // averageShares
            contentMetrics._3, // averageComments
            campaignMetrics._1
          )
        }
      }
        // Wait for all futures to complete
        Future.sequence(influencerDataFutures).map { influencerData =>
          // Sorting based on sortBy parameter
          val sortedInfluencers = sortBy match {
            case Some("followers") =>
              // Sort by followers, in descending order
              influencerData.sortBy(_.influencer.followers)(Ordering[Int].reverse)
            case Some("averageInteraction") =>
              // Sort by average interaction, in descending order
              influencerData.sortBy(_.campaignEngagement)(Ordering[Double].reverse)
            case _ =>
              // Default case: no sorting, return as is
              influencerData
          }

          // Return the sorted influencers to the view
          Ok(views.html.influencers(sortedInfluencers))
        }
    }
  }

}