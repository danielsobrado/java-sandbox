package com.jds.helloWorld;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class HelloWorld extends AbstractBehavior<HelloWorld.Command> {

    private HelloWorld(ActorContext<Command> context) {
        super(context);
    }

    interface Command {}

    public enum Greet implements Command {
        INSTANCE;
    }

    public static class ChangeMessage implements Command {
        public final String newMessage;

        public ChangeMessage(String newMessage) {
            this.newMessage = newMessage;
        }

    }

    public static Behavior<Command> create() {
        return Behaviors.setup(context -> new HelloWorld(context));
    }

    private String message = "Hello World";

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
            .onMessageEquals(Greet.INSTANCE, this::onGreet)
            .onMessage(ChangeMessage.class, this::onChangeMessage)
            .build();
    }

    private Behavior<Command> onChangeMessage(ChangeMessage command) {
        this.message = command.newMessage;
        return this;
    }

    private Behavior<Command> onGreet() {
        getContext().getLog().info(message);
        return this;
    }

}

