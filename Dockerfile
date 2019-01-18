# Set the base image
FROM openjdk:8-jre-alpine

ARG VERSION
# Create app and data directories
RUN mkdir -p /app
WORKDIR /app

# Download latest distribution inside image
# Extract package into /app stripping top level directory contained within the zip.
ADD ./kafka-webview-ui/target/kafka-webview-ui-$VERSION-bin.zip .
RUN unzip -d /app kafka-webview-ui-*-bin.zip && \
    rm -f /app/kafka-webview-ui-*-bin.zip && \
    f=`ls` && \
    mv /app/*/* /app && \
    rmdir $f && \
    rm -f /app/kafka-webview-ui-*-sources.jar && \
    rm -f /app/kafka-webview-ui-*-javadoc.jar && \
    apk add --update bash && \
    rm -rf /var/cache/apk/*

# Create volume to persist data
VOLUME /app/data

RUN chown -R nobody:nobody /app && \
    chown -R nobody:nobody /app/data

USER nobody
# Expose port
EXPOSE 8080

# What to run when the container starts
ENTRYPOINT [ "/app/start.sh" ]
