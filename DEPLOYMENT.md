# Backend Deployment Steps

## 1. Docker Image

Navigate to the backend application's directory and build the Docker image:

```bash
docker build -t marlonmilesmg/user-registration-backend:latest .
```

## 2. Push Docker Image to Docker Hub
Push the newly created backend image to your Docker Hub repository:

```bash
docker push marlonmilesmg/user-registration-backend:latest
```

## 3. Apply Deployment
Apply the Kubernetes deployment configuration for the backend from deployment.yaml:

```bash
kubectl apply -f deployment.yaml
```

## 4. Check Deployment Status
Ensure the deployment is rolling out successfully:

```bash
kubectl rollout status deployment/user-registration-backend
```

## 5. Describe Deployment
Get detailed information about the backend deployment:

```bash
kubectl describe deployment user-registration-backend
```

## 6. Verify Port Forwarding (if necessary)
Start port-forwarding for the backend service:

```bash
kubectl port-forward service/user-registration-backend 8080:8080
```

## 7. Health Checks (optional)
You can check the status of the application using the following endpoints:

```bash
curl http://localhost:8080/actuator/health

curl http://localhost:8080/actuator/info

curl http://localhost:8080/actuator/metrics
```

## References

Backend Docker Hub: User Registration Backend

https://hub.docker.com/repository/docker/marlonmilesmg/user-registration-backend/general