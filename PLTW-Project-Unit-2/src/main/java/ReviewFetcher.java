import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReviewFetcher {
    private static final String REVIEWS_FILE_PATH = "collected_avatar_reviews.csv";
    private static final String IMDB_URL = "https://www.imdb.com/title/tt9018736/reviews/?ref_=tt_ov_rt";

    public static void extractAndSaveReviews() {
        try (BufferedWriter reviewWriter = new BufferedWriter(new FileWriter(REVIEWS_FILE_PATH))) {
            reviewWriter.write("Username|Title|Review Text|Rating|Review Date\n");

            Elements reviewElements = Jsoup.connect(IMDB_URL).get().select(".review-container");
            for (Element reviewElement : reviewElements) {
                String user = reviewElement.select(".display-name-link a").text();
                String reviewTitle = reviewElement.select(".title").text();
                String reviewBody = reviewElement.select(".text.show-more__control").text();
                String reviewDate = reviewElement.select(".review-date").text();
                // Note: Assuming rating extraction requires additional logic
                String reviewRating = fetchRating(reviewElement);

                reviewWriter.write(String.format("%s|%s|%s|%s|%s\n", user, reviewTitle, reviewBody, reviewRating, reviewDate));
            }

            System.out.println("Review collection process completed successfully.");
        } catch (IOException e) {
            System.err.println("Failed to write reviews: " + e.getMessage());
        }
    }

    private static String fetchRating(Element review) {
        // Placeholder for future logic to extract ratings, if available
        return "Not Rated";
    }

    public static void main(String[] args) {
        extractAndSaveReviews();
    }
}
