package com.insert.ogbsm.service.meister;

import com.insert.ogbsm.domain.user.Student;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterDetailResponse;
import lombok.RequiredArgsConstructor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class MeisterParsingProvider {

    private final OkHttpClient httpClient;
    private final String GET_SCORE_URL = "https://bssm.meistergo.co.kr/_suCert/bssm/B002/jnv_201j.php";
    private final String GET_POINT_URL = "https://bssm.meistergo.co.kr/ss/ss_a40j.php";
    private final MeisterAuthProvider meisterAuthProvider;

    private static float getScore(Matcher scoreMatch, float score) {
        if (scoreMatch.find()) {
            score = Float.parseFloat(scoreMatch.group().split("<")[1].substring(3));
        }
        return score;
    }

    public MeisterDetailResponse getAllInfo(Student student) throws IOException {
        String scoreHtmlContent = getScore(student);
        String pointHtmlContent = getPoint();

        float score = 0;
        int positivePoint = 0;
        int negativePoint = 0;
        float basicJobSkills = 0;
        float professionalTech = 0;
        float workEthic = 0;
        float humanities = 0;
        float foreignScore = 0;

        Matcher scoreMatch = Pattern.compile("<td>[\\d.]*<\\/td>").matcher(scoreHtmlContent);
        Matcher positiveMatch = Pattern.compile("(\\(상점 : [0-9]*)").matcher(pointHtmlContent);
        Matcher negativeMatch = Pattern.compile("(\\(벌점 : [0-9]*)").matcher(pointHtmlContent);

        if (scoreMatch.find()) {
            score = Float.parseFloat(scoreMatch.group().split("<")[1].substring(3));
        }

        basicJobSkills = getScore(scoreMatch, score);
        professionalTech = getScore(scoreMatch, score);
        workEthic = getScore(scoreMatch, score);
        humanities = getScore(scoreMatch, score);
        foreignScore = getScore(scoreMatch, score);

        while (positiveMatch.find()) {
            positivePoint += Integer.parseInt(positiveMatch.group().split(" ")[2]);
        }
        while (negativeMatch.find()) {
            negativePoint += Integer.parseInt(negativeMatch.group().split(" ")[2]);
        }

        meisterAuthProvider.logout();
        return MeisterDetailResponse.builder()
                .scoreHtmlContent(scoreHtmlContent)
                .pointHtmlContent(pointHtmlContent
                        .replaceAll("\\(\\)", ""))
                .score(score)
                .positivePoint(positivePoint)
                .negativePoint(negativePoint)
                .basicJobSkills(basicJobSkills)
                .professionalTech(professionalTech)
                .workEthic(workEthic)
                .humanities(humanities)
                .foreignScore(foreignScore)
                .build();
    }

    public MeisterDetailResponse getScoreInfo(Student student) throws IOException {
        String scoreHtmlContent = getScore(student);
        float score = 0;
        float basicJobSkills = 0;
        float professionalTech = 0;
        float workEthic = 0;
        float humanities = 0;
        float foreignScore = 0;

        Matcher scoreMatch = Pattern.compile("<td>[\\d.]*<\\/td>").matcher(scoreHtmlContent);
        if (scoreMatch.find()) {
            score = Float.parseFloat(scoreMatch.group().split("<")[1].substring(3));
        }

        basicJobSkills = getScore(scoreMatch, score);
        professionalTech = getScore(scoreMatch, score);
        workEthic = getScore(scoreMatch, score);
        humanities = getScore(scoreMatch, score);
        foreignScore = getScore(scoreMatch, score);

        return MeisterDetailResponse.builder()
                .scoreHtmlContent(scoreHtmlContent)
                .score(score)
                .basicJobSkills(basicJobSkills)
                .professionalTech(professionalTech)
                .workEthic(workEthic)
                .humanities(humanities)
                .foreignScore(foreignScore)
                .positivePoint(0)
                .negativePoint(0)
                .build();
    }

    public String getScore(Student student) throws IOException {
        Request request = new Request.Builder()
                .url(GET_SCORE_URL)
                .post(new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("caseBy", "getViewer")
                        .addFormDataPart("uniqNo", student.getStudentId())
                        .build()
                )
                .build();

        Response response = httpClient.newCall(request).execute();
        return Objects.requireNonNull(response.body()).string();
    }

    public String getPoint() throws IOException {
        Request request = new Request.Builder()
                .url(GET_POINT_URL)
                .post(new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("caseBy", "listview")
                        .addFormDataPart("pageNumber", "1")
                        .addFormDataPart("onPageCnt", "100")
                        .build()
                )
                .build();

        Response response = httpClient.newCall(request).execute();
        return Objects.requireNonNull(response.body()).string();
    }

}
