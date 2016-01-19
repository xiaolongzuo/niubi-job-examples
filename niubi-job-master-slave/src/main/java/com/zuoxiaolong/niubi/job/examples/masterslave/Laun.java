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

import com.zuoxiaolong.niubi.job.core.helper.ClassHelper;
import com.zuoxiaolong.niubi.job.scanner.JobScanClassLoaderFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Xiaolong Zuo
 * @since 16/1/20 03:19
 */
public class Laun {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
         ClassLoader classLoader =       JobScanClassLoaderFactory.createClassLoader(Thread.currentThread().getContextClassLoader()
                ,
                "/Users/zuoxiaolong/project/intellij/niubi-job-examples/niubi-job-master-slave/target/classes/niubi-job-example-spring-1.0-SNAPSHOT.jar");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Class<?> c = null;
                try {
                    c = ClassHelper.getDefaultClassLoader().loadClass("com.zuoxiaolong.niubi.job.examples.masterslave.MasterSlaveNodeTest");
                    Object ins = c.newInstance();
                    Method method = c.getMethod("start", new Class[]{});
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
