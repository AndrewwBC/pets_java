![image](https://github.com/user-attachments/assets/e15668b2-fa44-4bb5-8bb0-650c95519aa4)# Spring Boot - Back-end of Pets4Ever - A brief doc.

This was my very first use of Spring Boot. As soon as I learned how to use, I decided to use in this project. I'll show here some features in this back-end.

## API REST

The API was documented with swagger: https://api.pets4ever.site/swagger-ui/index.html

## Handling Exceptions

Is really usefull in orden to create responses that can be understood by the user, like email already in use.

![screenshot](https://github.com/user-attachments/assets/99c36cf9-a421-43f2-82bf-95859bb03e38)

## Cookies

Cookies are safier than local storage, it can't be modified with JS.

![screenshot](https://github.com/user-attachments/assets/5045645d-29bd-421a-9325-83a659345e7e)

## Blue-green deployment

I used two VMs in Oracle Cloud to implement the blue-green deployment strategy.
By managing the keys in the deploy.yml file, I controlled which VM received the deployment.

![image](https://github.com/user-attachments/assets/6cea215c-d324-47f3-8727-3fede1c755ae)

## Nginx Web Server and Reverse Proxy

All requests are directed to the blue VM, which can either forward them to the 
local back-end or redirect them to the back-end deployed on the green VM.

![Captura de tela 2025-02-07 001002](https://github.com/user-attachments/assets/c3b02871-aa9e-4fa2-bf1a-d18b5e2fa835)








