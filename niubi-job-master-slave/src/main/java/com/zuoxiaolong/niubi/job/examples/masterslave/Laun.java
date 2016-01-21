/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zuoxiaolong.niubi.job.examples.masterslave;

import com.zuoxiaolong.niubi.job.core.helper.StringHelper;
import com.zuoxiaolong.niubi.job.scanner.ApplicationClassLoaderFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Xiaolong Zuo
 * @since 16/1/20 03:19
 */
public class Laun {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        ClassLoader classLoader =      ApplicationClassLoaderFactory.createNormalApplicationClassLoader(Laun.class.getClassLoader(),
                "D:/project/niubi-job-examples/niubi-job-master-slave/target/classes/niubi-job-example-spring-1.0-SNAPSHOT.jar",
                "D:/project/niubi-job-examples/niubi-job-master-slave/target/classes/niubi-job-cluster-1.0-SNAPSHOT.jar",
                "D:/project/niubi-job-examples/niubi-job-master-slave/target/classes/niubi-job-spring-1.0-SNAPSHOT.jar");
//        try {
//            System.out.println(classLoader.loadClass("com.zuoxiaolong.niubi.job.spring.container.DefaultSpringContainer"));
//            Class<Container> containerClass = (Class<Container>) classLoader.loadClass("com.zuoxiaolong.niubi.job.spring.container.DefaultSpringContainer");
//            Constructor<Container> containerConstructor = containerClass.getConstructor(Configuration.class, String.class, String[].class);
//            ClassHelper.overrideThreadContextClassLoader(classLoader);
//            Container container = containerConstructor.newInstance(new Configuration(classLoader), "", new String[]{});
//            System.out.println(container);
//        } catch (Exception e) {
//            throw new NiubiException(e);
//        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Class<?> c = null;
                try {
                    c = classLoader.loadClass("com.zuoxiaolong.niubi.job.cluster.node.MasterSlaveNode");
                    Constructor constructor = c.getConstructor(String.class, String.class, String[].class);
                    Object ins = constructor.newInstance("localhost:2181,localhost:3181,localhost:4181", "http://localhost:8080/job/masterSlave", StringHelper.emptyArray());
                    Method method = c.getMethod("join", new Class[]{});
                    method.invoke(ins, new Object[]{});
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setContextClassLoader(classLoader);
        thread.start();

        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
}
