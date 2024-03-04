package server;

import dataAccess.DataAccessException;
import exceptions.ResponseException;
import service.ClearService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {
    ClearService clear = new ClearService();

    public ClearHandler() throws ResponseException, DataAccessException {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        clear.clearDB();
        return "";
    }
}
