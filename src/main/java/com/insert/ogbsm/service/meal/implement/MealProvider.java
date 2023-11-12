package com.insert.ogbsm.service.meal.implement;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.insert.ogbsm.presentation.meal.dto.RawMealItemDto;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MealProvider {

    private final OkHttpClient httpClient;

    @Value("${neis.meal-url}")
    private String MEAL_API_URL;

    public List<RawMealItemDto> getRawMonthMealList(YearMonth date) throws IOException {
        String url = getUrl(date);
        Response mealResponse = sendRequest(url);
        JsonElement element = JsonParser.parseString(Objects.requireNonNull(mealResponse.body()).string());

        JsonArray jsonElements = element
                .getAsJsonObject().get("mealServiceDietInfo")
                .getAsJsonArray().get(1)
                .getAsJsonObject().get("row")
                .getAsJsonArray();

        Type RawMealList = new TypeToken<List<RawMealItemDto>>() {
        }.getType();
        return new Gson().fromJson(jsonElements, RawMealList);
    }

    @NotNull
    private Response sendRequest(String url) throws IOException {
        Request mealRequest = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response mealResponse = httpClient.newCall(mealRequest).execute();
        return mealResponse;
    }

    @NotNull
    private String getUrl(YearMonth date) {
        String dateParam = "MLSV_YMD=" + date.getYear() + String.format("%02d", date.getMonthValue());
        return MEAL_API_URL + dateParam;
    }

}
