# Interview Project Documentation
## Database

```plantuml
hide circle
skinparam linetype ortho

entity "device" as e01 {
  *id : text
  --
  *name : text
  *service_cost : number
  *device_type : device_type <<FK>>
  *services : service * <<FK>>
}

entity "device_type" as e02 {
  *id : text
  --
  *name : text
  *service_cost : number
}

entity "service" as e03 {
  *id : text
  --
  *name : text
  *cost : number
  accepted_devices : device_type * <<FK>>
}

e02 ||-- e01
e03 }o-- e01
e02 }|-- }| e03
@enduml
```
## Architecture
![enter image description here](https://miro.medium.com/v2/format:webp/0*rFs1UtU4sRns5vCJ.png)

## API

base path: ```http://localhost:8080/rmm```

### Device Type
base path: ```http://localhost:8080/rmm/v1/device-type```

#### Add device type
method: ```POST```

request body:
```json
{
  "name": {text},
  "cost": {numeric}
}
```

response body:
```json
{
  "id": {text},
  "name": {text},
  "cost": {numeric}
}
```

#### Delete device type
method: ```DELETE```

url: ```http://localhost:8080/rmm/v1/device-type/{device-type-id}```


### Service
base path: ```http://localhost:8080/rmm/v1/service```

#### Add service
method: ```POST```

request body:
```json
{
  "name": {text},
  "cost": {numeric},
  "acceptedDevices": [{device-type-id},...]
}
```

response body:
```json
{
  "id": {text},
  "name": {text},
  "cost": {numeric},
  "acceptedDevices": [
    {
      "id": {text},
      "name": {text},
      "cost": {numeric}
    },
    ...
  ]
}
```

#### Delete service
method: ```DELETE```

url: ```http://localhost:8080/rmm/v1/service/{service-id}```


### Device
base path: ```http://localhost:8080/rmm/v1/device```

#### Add device
method: ```POST```

request body:
```json
{
  "name": {text},
  "deviceType": {device-type-id}
}
```

response body:

```json
{
  "id": {
    text
  },
  "name": {
    text
  },
  "deviceType": {
    "id": {
      text
    },
    "name": {
      text
    },
    "cost": {
      numeric
    }
  },
  "serviceList": [
    {
      "id": {text},
      "name": {text},
      "cost": {numeric},
      "acceptedDevices": [
        {
          "id": {text},
          "name": {text},
          "cost": {numeric}
        },
        ...
      ]
    },
    ...
  ]
}
```

#### Delete device
method: ```DELETE```

url: ```http://localhost:8080/rmm/v1/device/{device-id}```

#### Assign service
method: ```POST```

url: ```http://localhost:8080/rmm/v1/device/assign/device-id/{device-id}/service-id/{service-id}```

response body:

```json
{
  "id": {
    text
  },
  "name": {
    text
  },
  "deviceType": {
    "id": {
      text
    },
    "name": {
      text
    },
    "cost": {
      numeric
    }
  },
  "serviceList": [
    {
      "id": {text},
      "name": {text},
      "cost": {numeric},
      "acceptedDevices": [
        {
          "id": {text},
          "name": {text},
          "cost": {numeric}
        },
        ...
      ]
    },
    ...
  ]
}
```

#### Unassign service
method: ```POST```

url: ```http://localhost:8080/rmm/v1/device/unassign/device-id/{device-id}/service-id/{service-id}```

response body:

```json
{
  "id": {
    text
  },
  "name": {
    text
  },
  "deviceType": {
    "id": {
      text
    },
    "name": {
      text
    },
    "cost": {
      numeric
    }
  },
  "serviceList": [
    {
      "id": {text},
      "name": {text},
      "cost": {numeric},
      "acceptedDevices": [
        {
          "id": {text},
          "name": {text},
          "cost": {numeric}
        },
        ...
      ]
    },
    ...
  ]
}
```

#### Calculate service cost by device
method: ```GET```

url: ```http://localhost:8080/rmm/v1/device/calculate-services/{device-id}```

response: numeric

#### Calculate total cost by device
method: ```GET```

url: ```http://localhost:8080/rmm/v1/device/calculate-total/{device-id}```

response: numeric

#### Calculate total cost
method: ```GET```

url: ```http://localhost:8080/rmm/v1/device/calculate-total```

response: numeric



