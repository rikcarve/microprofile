# microprofile
The main app using eclipse microprofile on wildfly-swarm

### start jaeger (all-in-one)
docker run --rm -p5775:5775/udp -p6831:6831/udp -p6832:6832/udp -p5778:5778 -p16686:16686 -p14268:14268 --name=jaeger jaegertracing/all-in-one:latest
