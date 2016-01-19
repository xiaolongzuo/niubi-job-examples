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

import com.zuoxiaolong.niubi.job.cluster.node.MasterSlaveNode;
import com.zuoxiaolong.niubi.job.core.helper.StringHelper;
import com.zuoxiaolong.niubi.job.scheduler.node.Node;

import java.io.IOException;

/**
 * @author Xiaolong Zuo
 * @since 16/1/9 15:08
 */
public class MasterSlaveNodeTest {

    public void start() throws IOException {
        Node node = new MasterSlaveNode("localhost:2181,localhost:3181,localhost:4181", "http://localhost:8080/job/masterSlave", StringHelper.emptyArray());
        node.join();
    }

}
