package ClientView;

public class ClientSelectWindow {
    public static final String SELECT = " SELECT ";
    public static final String AND = " AND ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";

    public static final String INSERT = " INSERT INTO ";
    public static final String VALUES = " VALUES ";
    public static final String DELETE = " DELETE ";
    public ClientEnterWindow enterView;
    public ClientSelectWindow(ClientEnterWindow clientEnterWindow){
        enterView = clientEnterWindow;
        Init();
    }

    private void Init() {

    }

}
