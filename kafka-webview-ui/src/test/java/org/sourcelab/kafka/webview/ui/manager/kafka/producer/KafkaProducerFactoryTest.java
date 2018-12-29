/**
 * MIT License
 *
 * Copyright (c) 2017, 2018 SourceLab.org (https://github.com/Crim/kafka-webview/)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.sourcelab.kafka.webview.ui.manager.kafka.producer;

import com.salesforce.kafka.test.junit4.SharedKafkaTestResource;
import org.junit.ClassRule;
import org.junit.Test;
import org.sourcelab.kafka.webview.ui.manager.kafka.KafkaClientConfigUtil;
import org.sourcelab.kafka.webview.ui.manager.kafka.config.ClusterConfig;
import org.sourcelab.kafka.webview.ui.manager.kafka.producer.config.WebProducerConfig;
import org.sourcelab.kafka.webview.ui.manager.kafka.producer.transformer.StringTransformer;

import static org.junit.Assert.assertNotNull;

public class KafkaProducerFactoryTest {

    @ClassRule
    public static SharedKafkaTestResource sharedKafkaTestResource = new SharedKafkaTestResource();

    private final KafkaClientConfigUtil configUtil = new KafkaClientConfigUtil(
        "n/a",
        "KafkaWebView"
    );

    private final KafkaProducerFactory factory = new KafkaProducerFactory(configUtil);

    /**
     * Smoke test the factory instance.
     */
    @Test
    public void smokeTest() {
        // Defines the Cluster
        final ClusterConfig clusterConfig = ClusterConfig.newBuilder()
            .withBrokerHosts(sharedKafkaTestResource.getKafkaConnectString())
            .build();

        // Create a config
        final WebProducerConfig config = WebProducerConfig.newBuilder()
            .withKeyTransformer(new StringTransformer())
            .withValueTransformer(new StringTransformer())
            .withProducerClientId("MyClientId")
            .withClusterConfig(clusterConfig)
            .withTopic("MyTopic")
            .build();

        final WebKafkaProducer kafkaProducer = factory.createWebProducer(config);
        assertNotNull(kafkaProducer);

        // TODO validate it?
    }
}