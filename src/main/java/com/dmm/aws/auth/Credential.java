package com.dmm.aws.auth;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class Credential {
  public static ProfileCredentialsProvider credential;
  public static ClientConfiguration clientConfig;

  public static void createCredential(String profile) {
    credential = new ProfileCredentialsProvider(profile);
  }

  public static void createConfiguration() {
    clientConfig = new ClientConfiguration().withProtocol(Protocol.HTTP);
  }
}
