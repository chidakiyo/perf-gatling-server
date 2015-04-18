import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.HeaderNames._
import scala.concurrent.duration._

class Step1 extends Simulation {

  val URI = "http://localhost"
  val PATH = "/"
  val TERM = 1
  val ACCESSNUM = 1

  val httpProtocol =
    http
      .baseURL(URI)
      .acceptCharsetHeader("ISO-8859-1,utf-8;q=0.7,*;q=0.7")
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
      .disableFollowRedirect
      .userAgentHeader("Chrome/41.0.2272.118")

  val scn =
    scenario("Getting Started Scenario")
      .exec {
        http("simpleRequest")
          .get(PATH)
          .check(status.is(200)) // 返却されるHTTPステータスが200であること
      }

  setUp(scn.inject(rampUsers(ACCESSNUM * TERM) over (TERM seconds)))
    .protocols(httpProtocol)
    .assertions(global.successfulRequests.percent.is(100)) // すべてのリクエストが期待する結果であること

}