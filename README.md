# Red Hat Services Lab

Repositorio con una un microservicio en Spring Boot de ejemplo para desplegar en Openshift Container Platform versión 3.11 con Dockerfile.

## Descripción de funcionalidad

Esta ruta de integración expone un servicio Rest que en el request recibe un número de cuenta y devuelve el saldo de la cuenta en formato JSON.

## URL

> Reemplazar **{HOST_OCP}** por el host de ruta generada en el ambiente de OCP desplegado
```
http://{HOST_OCP}/consultaSaldo
```

## Request

```JSON
{
    "numeroCuenta": "12222"
}
```

## Response

```JSON
{
    "saldo": 2300000,
    "fecha": "2020-11-18T20:12:57.079+00:00"
}
```

## Pasos de instalación de OCP

A continuación se describen los pasos para realizar el despliegue de este microservicio en un ambiente de OCP 3.11 o 4.x, una vez se haya clonado este repositorio

> Ubicarse en el proyecto

```
cd redhat-services-lab
```

> Compilar y generar el Jar
```
mvn clean package -DskipTests=true
```

> Se crea el directorio para copiar el jar para desplegar
```
mkdir despliegue/lib
```

> Se copia el jar generado a la carpeta lib
```
cp target/consulta-saldo*.jar despliegue/lib
```

> Autenticarse en OpenShift, se debe reemplazar los siguientes campos **{{HOST_CONSOLE_OCP}}}**, **{{PORT_CONSOLE_OCP}}**, **{{USER_OCP}}**, **{{PASSWORD_OCP}}**
```
oc login --server=https://{{HOST_CONSOLE_OCP}}}:{{PORT_CONSOLE_OCP}} --username={{USER_OCP}} --password={{PASSWORD_OCP}} --insecure-skip-tls-verify=true
```

> Crear el proyecto donde se van a desplegar las aplicaciones, si el proyecto ya esta creado se puede omitir este paso
```
oc new-project integration-services-lab --display-name="Lab Servicios"
```

> Ubicarse dentro del proyecto creado
```
oc project integration-services-lab
```

> Nos movemos a la carpeta de despliegue
```
cd despliegue
```

> Crear el config map con la parametrización
```
oc create -f yml/configmap-consulta-saldo.yml -n integration-services-lab
```

> Se crea la aplicación indicando que es por docket
```
oc new-app --name=consulta-saldo --strategy docker ./ -n integration-services-lab
```

> Se inicia el build indicando la carpeta donde esta el archivo dockerfile y el jar generado
```
oc start-build consulta-saldo --from-dir ./ -n integration-services-lab
```

> Se actualiza la versión de la nueva imagen generada, se debe reemplazar **{{VERSION_APP}}** por la versión del artefacto en el pom.xml
```
oc tag consulta-saldo:latest consulta-saldo:{{VERSION_APP}} -n integration-services-lab
```

> Se actualiza el deployment
```
oc apply -f yml/deployment.yml
```

> Se expone la ruta
```
oc expose svc consulta-saldo -n integration-services-lab
```

> Se obtiene la ruta generada para consumir el servicio
```
echo -en "\n\nhttp://$(oc get route consulta-saldo -o template --template={{.spec.host}} -n integration-services-lab)\n\n"
```

> Se consume el servicio con CURL, se debe reemplazar **{{RUTA_PASO_ANTERIOR}}** por la URL que se obtuvo en el paso anterior
```
curl --location --request POST '{{RUTA_PASO_ANTERIOR}}/consultaSaldo' \
--header 'Content-Type: application/json' \
--data-raw '{
    "numeroCuenta": "52122344255"
}'
```

## Author

* **Lázaro Miguel Coronado Torres** - *Middleware Senior Consultant - lcoronad@redhat.com* 
