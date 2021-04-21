package edu.javacourse.register.busines;

import edu.javacourse.register.dao.MarriageDao;
import edu.javacourse.register.view.MarriageRequest;
import edu.javacourse.register.view.MarriageResponse;

public class MarriageManager {

    //если для каждого объекта задаются поля и для каждого запроса поля будут разные, то 1 объект для всех
    //запросов будет создать невозможно - это если бы 1 ложкой пытались есть 15 человек
    private String setting;

    public MarriageResponse findMarriageCertificate(MarriageRequest request) {
        MarriageDao dao = new MarriageDao();
        throw new UnsupportedOperationException("Unsupported");
    }

}
