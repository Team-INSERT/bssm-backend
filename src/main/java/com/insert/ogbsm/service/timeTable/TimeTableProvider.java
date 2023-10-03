package com.insert.ogbsm.service.timeTable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.insert.ogbsm.domain.timeTable.TimeTable;
import com.insert.ogbsm.presentation.timeTable.dto.RowTimeTableReq;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class TimeTableProvider {
    private final OkHttpClient httpClient;

    @Value("${env.neis.time_table_url}")
    private String TIME_TABLE_API_URL;

    @Nullable
    private static List<RowTimeTableReq> getTimeTableDtos(JsonElement element) {
        JsonArray jsonElements = element
                .getAsJsonObject().get("hisTimetable")
                .getAsJsonArray().get(1)
                .getAsJsonObject().get("row")
                .getAsJsonArray();

        Type RowTimeTableList = new TypeToken<List<RowTimeTableReq>>() {
        }.getType();

        return new Gson().fromJson(jsonElements, RowTimeTableList);
    }

    public List<TimeTable> getRowWeekTimeTableList(LocalDate date) throws IOException {
        String url = getUrl(date);
        Response timteTableResponse = sendRequest(url);
        JsonElement element = JsonParser.parseString(requireNonNull(timteTableResponse.body()).string());

        return requireNonNull(getTimeTableDtos(element))
                .stream()
                .map(RowTimeTableReq::toEntity)
                .toList();
    }

    @NotNull
    private Response sendRequest(String url) throws IOException {
        Request mealRequest = new Request.Builder()
                .url(url)
                .get()
                .build();
        return httpClient.newCall(mealRequest).execute();
    }

    private String getUrl(LocalDate date) {

        return TIME_TABLE_API_URL + "ALL_TI_YMD=" + date.format(DateTimeFormatter.BASIC_ISO_DATE);
    }
}
