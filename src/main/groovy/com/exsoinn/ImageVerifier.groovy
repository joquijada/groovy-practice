package standalone.image

import com.opencsv.bean.StatefulBeanToCsv
import com.opencsv.bean.StatefulBeanToCsvBuilder
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import groovy.util.logging.Slf4j
import me.anyti.core.DisplayType
import me.anyti.transform.LocalStatic
import me.anyti.util.groovy.TveProcessConfiguration
import me.anyti.util.image.ImageChooserConfig
import standalone.StandaloneApp
import wslite.rest.RESTClient


/**
 * Script that can be used to generate a report of images returned by a SHO endpoint. Can be pointed at any environment host, simply update the final
 * "base" constant. For every endpoint you want to invoke you must supply a Groovy closure that knows how to deal with the output (typically JSON) returned
 * by that endpoint in order to extract fields to populate a ResponseData object (an inner class of this script) and return a list of such objects - that's
 * the contract that such closure should obey. See standalone.image.ImageVerifier#seriesClosure() and standalone.image.ImageVerifier#episodeForSeriesClosure()
 * for examples. Then after you've done that, add the endpoint/closure combo to the "endPoints" Map. The script will invoke each endPoint for all unique
 * combinations of displayType/platform configured in ImageChooserConfig, test whether the image URL's returned exists or not, and generate a CSV report
 * in a file named "image-report.csv". For an example output of this script, see Google sheet at https://docs.google.com/spreadsheets/d/1qLac1nfLChfNbjKBlraigEw47fWd2UO7TGjDTwqcnrg/edit?usp=sharing.
 * Comments/feedback/questions/enhancements are welcomed.
 */
@Slf4j
@TveProcessConfiguration
@CompileStatic
class ImageVerifier extends StandaloneApp {

  //placeholder
  public static void main(String[] args) {}

  void doActualWork(final String[] args) {
    @LocalStatic final String SERIES_REPORT = "series-report"
    @LocalStatic final String TITLE_REPORT = "title-report"


    def displayTypeToPlatform = prepareDisplayTypeAndPlatforms() as Map

    def final base = 'https://qa-maint.sho.com'
    def endPoints = ['/api/titles/series/1033823': episodeForSeriesClosure()]
    def reportType = args && args.length > 0 ? args[0] : TITLE_REPORT
    if (reportType == SERIES_REPORT) {
      endPoints = ['/api/series/1033823': seriesClosure()]
    }
    invokeEndpoints(displayTypeToPlatform, endPoints, base)
  }


  @CompileStatic(TypeCheckingMode.SKIP)
  private void invokeEndpoints(Map<DisplayType, List<String>> displayTypeToPlatform, Map<String, Closure> endPoints, String base) {
    def slurper = new JsonSlurper()
    def testImage = testImageUrlClosure()
    List<ImageReportLine> reportLines = []
    displayTypeToPlatform.each { displayType, platforms ->
      platforms.each { platform ->
        endPoints.each { endPoint, closure ->
          def fullUrl = base + endPoint
          def show = new RESTClient(fullUrl)
          def response = show.get(['headers': ['x-stat-displaytype': displayType.toString(), 'x-stat-os': platform]])
          if (response.statusCode == 200) {
            def res = slurper.parseText(response.response.contentAsString)
            def responseDataList = closure(res)

            responseDataList.each {
              it.typeToImages.each { type, urls ->
                if (!(type in ['TITLE_FEED_DETAILS_EPISODE_LANDSCAPE', 'TITLE_FEED_DETAILS_EPISODE', 'SERIES_EPISODE_LIST_DISPLAY', 'SERIES_DETAIL_SMALL'])) {
                  return
                }
                urls.each { url ->
                  boolean exists = testImage(url)
                  def imageReportLine = new ImageReportLine(host: base, endPoint: endPoint, displayType: displayType, platform: platform, imageType: type, url: url,
                          imageUrlExists: exists, titleId: it.titleId)
                  reportLines << imageReportLine
                }
              }

            }
          } else {
            log.error('There was some error invoking $fullUrl, $response.statusCode')
          }
        }
      }
    }
    writeReport(reportLines)
  }

  /**
   * A closure that knows how to extract relevant fields from the JSON response of
   * the /api/titles/series/{seriesId} end point
   */
  @CompileStatic(TypeCheckingMode.SKIP)
  private Closure<List<ResponseData>> episodeForSeriesClosure() {
    { Object obj ->
      def responseDataList = []
      obj.episodesForSeries.each {
        def typeToImages = [:]
        it.images.each {
          typeToImages.get(it.type, []) << it.url
        }
        responseDataList << new ResponseData(typeToImages: typeToImages, titleId: it.id.toString())
      }
      responseDataList
    }
  }


  /**
   * A closure that knows how to extract relevant fields from the JSON response of
   * the /api/series/{seriesId} end point
   */
  @CompileStatic(TypeCheckingMode.SKIP)
  private Closure seriesClosure() {
    { Object obj ->
      def responseDataList = []
      def typeToImages = [:]
      obj.images.each {
        typeToImages.get(it.type, []) << it.url
      }
      responseDataList << new ResponseData(typeToImages: typeToImages, titleId: 'n/a')
      responseDataList
    }
  }


  @CompileStatic(TypeCheckingMode.SKIP)
  private Closure testImageUrlClosure() {
    { String url ->
      URLConnection urlConnection = new URL(url).openConnection()
      urlConnection.responseCode == urlConnection.HTTP_OK
    }
  }

  private void writeReport(List<ImageReportLine> lines) {
    log.info('Writing report with ' + lines.size() + ' lines...')
    Writer writer = new FileWriter("image-report.csv")
    StatefulBeanToCsv<ImageReportLine> imageReportCsv = new StatefulBeanToCsvBuilder<ImageReportLine>(writer).build()
    imageReportCsv.write(lines)
    writer.close()
    log.info('Done writing report')
  }


  /**
   * Dip into ImageChooserConfig to get a list of all the PreferredImage objects built, and from there prepare a list of unique displayType/platform
   * combo's
   */
  private prepareDisplayTypeAndPlatforms() {
    Map<DisplayType, Set<String>> displayTypeToPlatform = new LinkedHashMap<>()

    ImageChooserConfig.config.each { displayType, imageTypeToPreferredImage ->
      imageTypeToPreferredImage.each { _, preferredImages ->
        preferredImages.each {
          String platform
          switch (it.pattern) {
            case (~/.*(Roku|ROKU).*/):
              platform = 'roku'
              break
            case (~/(?i).*sparrow.*/):
              platform = 'sparrow'
              break
            case (~/.*(Appletv|APPLE_TV).*/):
              platform = 'Appletv'
              break
            case (~/.*(XboxOne|XBOX_ONE).*/):
              platform = 'XboxOne'
              break
            case (~/(?i).*samsung.*/):
              platform = 'samsung'
              break
            case (~/(?i).*tivo.*/):
              platform = 'tivo'
              break
            case (~/(?i).*tizen_tv.*/):
              platform = 'tizen_tv'
              break
            case (~/(?i).*(hotel_tv).*/):
              platform = 'hotel_tv'
              break
            case (~/(?i).*(lg_tv|hotel_tv).*/):
              platform = 'lg_tv'
              break
            case (~/(?i).*(android_tv|hilton_tv|oculus|directv_hotel).*/):
              platform = 'android_tv'
              break
            case (~/(?i).*tvos.*/):
              platform = 'roku'
              break
            case (~/.*/):
              platform = 'n/a'
              break
            default:
              platform = it.pattern.toString()
          }
          displayTypeToPlatform.get(displayType, [] as Set<String>) << platform
        }
      }
    }
    displayTypeToPlatform
  }

  private static class ResponseData {
    Map typeToImages
    String titleId

  }

  public static class ImageReportLine {
    String host
    String endPoint
    String displayType
    String platform
    String imageType
    String url
    boolean imageUrlExists
    String titleId
  }
}


