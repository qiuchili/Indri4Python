package tju.session.utils;

import py4j.GatewayServer;
public class StackEntryPoint {

    private Stack stack;

    public StackEntryPoint() {
      stack = new Stack();
      stack.push("Initial Item");
    }

    public void print() {
        System.out.println("Hello, world");
    }
    public Stack getStack() {
        return stack;
    }

    public static void main(String[] args) {
        GatewayServer gatewayServer = new GatewayServer(new StackEntryPoint());
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }

}
