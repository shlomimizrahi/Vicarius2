package com.example.vicarius2;

import io.micrometer.common.util.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.vicarius2")
public class Config {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.username}")
    private String elasticsearchUsername;

    @Value("${elasticsearch.password}")
    private String elasticsearchPassword;

    @Bean()
    public RestHighLevelClient elasticsearchC() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        if (!StringUtils.isEmpty(elasticsearchUsername)) {
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(elasticsearchUsername, elasticsearchPassword));
        }

        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                .setDefaultHeaders(compatibilityHeaders()));
    }

    private Header[] compatibilityHeaders() {
        return new Header[]{new BasicHeader(HttpHeaders.ACCEPT, "application/vnd.elasticsearch+json;compatible-with=7"), new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.elasticsearch+json;compatible-with=7")};
    }


    @Bean(name = "appExecutor")
    public Executor appExecutor() {
        return Executors.newCachedThreadPool();
    }

}
