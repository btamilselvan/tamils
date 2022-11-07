FROM nginx

# temporarily switch to root
USER root

# create the nginx runtime for kube
RUN mkdir -p /var/nginx/cache && \ 
    mkdir -p /var/nginx/run && \ 
    mkdir -p /var/nginx/log && \
    mkdir -p /var/nginx/tmp/client && \
    mkdir -p /var/nginx/tmp/proxy && \ 
    mkdir -p /var/nginx/tmp/fastegi && \ 
    mkdir -p /var/nginx/tmp/uwsgi && \
    mkdir -p /var/nginx/tmp/scgi && \ 
    chown -R 1001:0 /var/nginx && \ 
	chown -R 1001:0 /var/cache/nginx && \ 
    chmod -R g+w /var/nginx

WORKDIR /app/lumin

COPY nginx.conf /etc/nginx/nginx.conf

COPY nginx-fe.conf /etc/nginx/conf.d/nginx-fe.conf

#COPY nginx-storybook.conf /etc/nginx/conf.d/nginx-storybook.conf

RUN chown -R 1001:0 /etc/nginx

# Place the application from build stage

#COPY app/dist /deployments/app/

COPY app/dist /deployments/app
COPY app/storybook /deployments/app/storybook

#COPY --from-build/tmp/app/storybook /app/lumin/storybook

COPY health.html /deployments/app/

USER 1001

EXPOSE 9080

# run nginx

CMD ["-g", "daemon off;"]

ENTRYPOINT ["/usr/sbin/nginx"]