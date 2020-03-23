package com.alphathur.curatordemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorFactory {
  private static final String CONNECTION_STR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

  private static CuratorFramework curatorFramework = null;

  static {
    curatorFramework = CuratorFrameworkFactory.builder().connectString(CONNECTION_STR)
        .sessionTimeoutMs(3000).retryPolicy(
            new ExponentialBackoffRetry(1000, 5)).build();
    curatorFramework.start();
  }

  public static CuratorFramework getCuratorFramework() {
    return curatorFramework;
  }
}
