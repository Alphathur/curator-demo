package com.alphathur.curatordemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public class LockApp {

  private static CuratorFramework curatorFramework = CuratorFactory.getCuratorFramework();

  public static void main(String[] args) {
    final InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/locks");
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        try {
          System.out.println(Thread.currentThread().getName() + "->尝试竞争锁");
          interProcessMutex.acquire();
          System.out.println(Thread.currentThread().getName() + "->持有锁");
          Thread.sleep(3000);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            System.out.println(Thread.currentThread().getName() + "->释放锁");
            interProcessMutex.release();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }).start();
    }
  }
}
