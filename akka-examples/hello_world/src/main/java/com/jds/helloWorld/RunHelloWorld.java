package com.jds.helloWorld;

import akka.actor.typed.ActorSystem;

public class RunHelloWorld {
  
    public static void main(String[] args) {
        ActorSystem<HelloWorld.Command> system = ActorSystem.create(HelloWorld.create(), "MyHelloWorld");
        system.tell(HelloWorld.Greet.INSTANCE);
        system.tell(HelloWorld.Greet.INSTANCE);
        system.tell(new HelloWorld.ChangeMessage("Hello Akka!"));
        system.tell(HelloWorld.Greet.INSTANCE);
        system.tell(HelloWorld.Greet.INSTANCE);
    }
}
