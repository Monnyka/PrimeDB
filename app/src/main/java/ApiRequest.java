public class ApiRequest {


    private String requestRoute="https://api.themoviedb.org/3/";
    private String apiKey="?api_key=1469231605651a4f67245e5257160b5f";

    String requestTrending=requestRoute+"movie/now_playing"+apiKey+"&language=en-US&page=1";
}
