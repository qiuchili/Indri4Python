package tju.session.utils;

import py4j.GatewayServer;
public class IndriAPIEntryPoint{

    private IndriAPI indri;

    public IndriAPIEntryPoint() throws Exception{

        String indexDir = "E:/qiuchi/clueweb12/text_datasets/index";
        indri = new IndriAPI(indexDir);
    }

    public void print() {
        System.out.println("Hello, world");
    }
    public IndriAPI getIndriAPI() {
        return indri;
    }

    public static void main(String[] args) throws Exception{
        GatewayServer gatewayServer = new GatewayServer(new IndriAPIEntryPoint());
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }

}
