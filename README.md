# Red Hat Services Lab

Repositorio con una un microservicio en Spring Boot de ejemplo para desplegar en Openshift Container Platform versión 3.11 con Dockerfile.

## Descripción de funcionalidad

Esta ruta de integración expone un servicio Rest que en el request recibe un número de cuenta y devuelve el saldo de la cuenta en formato JSON.

## URL

Reemplazar {HOST_OCP} por el host de ruta generada en el ambiente de OCP desplegado
```
http://{HOST_OCP}/consultaSaldo
```

## Request

```
{
    "numeroCuenta": "12222"
}
```

## Response

```
{
    "saldo": 2300000,
    "fecha": "2020-11-18T20:12:57.079+00:00"
}
```

## Pasos de instalación de OCP

A continuación se describen los pasos para realizar el despliegue de este microservicio en un ambiente de OCP 3.11 o 4.x, una vez se haya clonado este repositorio

cd redhat-services-lab

mvn clean package -DskipTests=true

```
Si el proyecto ya esta creado se puede omitir este paso
```

oc new-project integration-services-lab --display-name="Lab Servicios"

oc project integration-services-lab

oc create -f despliegue/configmap-consulta-saldo.yml -n integration-services-lab

oc new-build --binary=true --name=consulta-saldo openshift/java:8 -n integration-services-lab

oc new-app integration-services-lab/consulta-saldo:latest --name=consulta-saldo --allow-missing-imagestream-tags=true -n integration-services-lab

oc set triggers dc/consulta-saldo --remove-all -n integration-services-lab

oc expose dc consulta-saldo --port 8080 -n integration-services-lab

oc start-build consulta-saldo --from-file=target/consulta-saldo-0.0.1-SNAPSHOT.jar --wait=true -n integration-services-lab

oc rollout latest dc/consulta-saldo -n integration-services-lab

oc expose svc consulta-saldo -n integration-services-lab

echo -en "\n\nhttp://$(oc get route consulta-saldo -o template --template={{.spec.host}} -n integration-services-lab)\n\n"

curl --location --request POST '{{RUTA_PASO_ANTERIOR}}/consultaSaldo' \
--header 'Content-Type: application/json' \
--data-raw '{
    "numeroCuenta": "52122344255"
}'

## Author

* **Lázaro Miguel Coronado Torres** - *Middleware Senior Consultant - lcoronad@redhat.com* 
