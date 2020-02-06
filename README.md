# hello-riff

Sample application intended for showing the app developer experience with [riff](https://projectriff.io) when buiding local source.

You can build the app and push the image to a registry and then create a riff Container resource referencing the image. When you re-build the image using the same tag the Container resource will reconcile the image sha and update the `latestImage` status message shown using the `riff container list` command.

Once the image is built you can create a Knative Deployer resource referencing the Container resource.

## clone this git repo

```sh
git clone git@github.com:trisberg/hello-riff.git
```

## local build using riff cli

```sh
riff app create hello-riff --local-path . --image $USER/hello-riff:v0.0.1
```

> *NOTE:* If you want to rebuild the app you need to first delete the application resource using `riff app delete hello-riff`

## local build using pack

```sh
pack build --builder index.docker.io/cloudfoundry/cnb --path . --publish $USER/hello-riff:v0.0.1
```

## local build using jib

```sh
./mvnw clean package jib:build -DskipTests -Dimage=$USER/hello-riff:v0.0.1
```

## create a riff container resource

```sh
riff container create hello-riff --image $USER/hello-riff:v0.0.1
```

## create a  knative deployer for the container

```sh
riff knative deployer create hello-riff --container-ref hello-riff --ingress-policy External --env SPRING_PROFILES_ACTIVE=cloud
```

## invoke the app

```sh
ingress=$(kubectl get svc -n projectcontour envoy-external -ojsonpath='{.status.loadBalancer.ingress[0].ip}')
curl ${ingress}/ -H 'Host: hello-riff.default.example.com' && echo
```

## gaps in the riff cli

- in order to rebuild the source you have to delete the Application resource. We could provide a `riff application build` command for `--local-path` builds.

- there is no way to update Deployer resources with new env vars or other settings. We could provide a `riff knative/core deployer update` command.
