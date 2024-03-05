package com.myorg;


import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;

import java.util.Map;

public class IacApp {
    public static void main(final String[] args) {
        App app = new App();

        new IacStack(app, "IacStack", StackProps.builder()
                .tags(Map.of("owner", "lesedij@bbd.co.za",
                        "created-using", "cdk"))
                .build());

        app.synth();
    }
}

