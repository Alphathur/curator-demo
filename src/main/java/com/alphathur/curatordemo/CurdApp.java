package com.alphathur.curatordemo;

import java.util.List;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;


public class CurdApp {

  private static CuratorFramework curatorFramework = CuratorFactory.getCuratorFramework();

  public static void main(String[] args) throws Exception {
//        create();
//        query();
//        create("/app1/node2", "1");
//        create("/app1/node3", "2");
//        create("/app1/node4", "3");
//        create("/app1/node5", "4");

//        queryAllNode("/app1");
//    update();
//    delete();
//    queryAllValue("/app1");
//    createTemp();
    queryAllValue("/app1");
    printStat();

  }

  private static void create() throws Exception {
    Object obj = curatorFramework.create().creatingParentsIfNeeded()
        .withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/app2/temp");
    System.out.println(obj);
  }

  private static void createTemp() throws Exception {
    Object obj = curatorFramework.create()
        .withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/app5/temp");
    System.out.println(obj);
  }

  private static void create(String path, String value) throws Exception {
    curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
        .forPath(path, value.getBytes());
  }

  private static void query() throws Exception {
    byte[] bytes = curatorFramework.getData().forPath("/app1/node1");
    System.out.println(new String(bytes));
  }

  private static void queryAllNode(String path) throws Exception {
    List<String> children = curatorFramework.getChildren().forPath(path);
    children.forEach(s -> System.out.println(s));
  }

  private static void queryAllValue(String path) throws Exception {
    List<String> children = curatorFramework.getChildren().forPath(path);
    children.forEach(s -> {
      String childPath = path + "/" + s;
      System.out.println(childPath);
      byte[] bytes = new byte[0];
      try {
        bytes = curatorFramework.getData().forPath(childPath);
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println(new String(bytes));
    });
  }

  private static void update() throws Exception {
    curatorFramework.setData().forPath("/app1/node1", "hello".getBytes());
  }

  private static void delete() throws Exception {
    curatorFramework.delete().deletingChildrenIfNeeded().forPath("/app1/node1");
  }

  private static void printStat() throws Exception {
    Stat stat = new Stat();
    curatorFramework.getData().storingStatIn(stat).forPath("/app1/node2");
    System.out.println(stat);
  }
}
