<h1 align="center">Easy Manager</h1>


<!--ts-->
   * [About](#about)
   * [How to use](#how-to-use)
   * [Credits](#credits)
<!--te-->

<h1>About</h1>
<p align="center">This repository contains an API (Application Programming Interface) developed in Java Spring that allows users to access and interact with the resources of an employee management system in a pragmatic way. The API provides a standardized interface for requests and responses between the system and users, allowing third parties to develop applications that integrate and utilize system resources quickly and efficiently.</p>
  </br>
<h1>How to use</h1>
<h2>Prerequisites</h2>
<p>First, you must have the MySql database installed on your computer, which can be downloaded through this <a href="https://www.mysql.com/downloads/">link</a>.</br>
<p>Then, <a href="https://insomnia.rest/download">download</a> Insomnia to make http requests.</br>
<p>After that, create a local database with name manageremployee and give it the root name and password of your choice, just make sure you change the spring.datasource.password variable in the application.properties to the password you set.</br></br>
  <img src="https://user-images.githubusercontent.com/63808405/210464300-8497e99d-b41d-4d57-8e41-87ba76e3c8fa.png"/>
  
  <p>Finally, enter the management of your google account, in the security tab and create app passwords, a token will be generated and you will need to pass your email and token in the settings of the email properties, for the email service to work.</br>
  </br>
  <img src="https://user-images.githubusercontent.com/63808405/210464535-78bc1e12-6b5a-490a-851b-9c90b2c93a30.png"/>
   <img src="https://user-images.githubusercontent.com/63808405/210464553-93c736f3-37dd-4008-9f7d-a051eb4cd8ef.png"/>
   
  <p>Change spring.username and spring.password to your google email and token.</br>
  <p>Run the app.</br>
  
<h2>Endpoints</h2>
<p>After doing all that, let's go to the endpoints.</br>
<h3>BASE URL</h3>

```bash
http://localhost:8080/api/v1/
``` 

To create a new manager, use:

```bash
@POST
employee/create-employee
``` 
with the request body

```bash
{	

"cnpj":987654321,
"mname":"Joao Ferreira",
"memail":"joaoferreira@hotmail.com",
"mcompany":"Dev Cast",
"mphone":"+5596123547895",
"isActive":1,
"gender":0,
"bornDay":"2020-12-31",
"password":"123"

  }
``` 
Where cnpj is the manager's identifier, mcompany the name of his company, gender which can be MALE and FEMALE with 0 and 1 respectively.
Done that, wait for this answer.

  <img src="https://user-images.githubusercontent.com/63808405/210465545-b5e632bf-92a1-4917-a1fc-799959e5df85.png"/></br></br>
  
  After that, we can create an employee for our manager, so use the method:
  
```bash
@PATCH
employee/create-employee
``` 

with the request body

```bash
{	

	"cnpj":987654321,
	"employees":
    [{
	  "cpf":12345678,
	  "ename":"Brad Pitt",
	  "eemail":"bradpitt@hotmail.com",
	  "ephone":"+5585631135657",
	  "isActive":1,
	  "gender":0,
	  "bornDay":"2000-12-31",
	  	"password":"123"
    }]

  }
``` 

Where cnpj is the manager's identifier and cpf is the employee identifier.
Done that, wait for this answer.

 <img src="https://user-images.githubusercontent.com/63808405/210466042-b61b5429-0fcf-4c93-85c3-e5c0912384df.png"/></br></br>
 
 The patch request is used because we only update the list of employees that a manager has, when the manager object is saved again, due to the existing relationship between manager and employee, an employee is automatically created.
 After that, we will create jobs for our employee!

```bash
@PATCH
/job/create
``` 

with the request body

```bash
{
	"cnpj":987654321,
	"cpf":12345678,
	"jobs":[{
		"name":"fazer tal coisa",
		"description":"bla bla bla",
		"finishDay":"2023-10-12"
	},
	 {
		"name":"fazer tal outra coisa",
		"description":"bla bla bla 2",
		"finishDay":"2023-10-16"
	},{
		"name":"fazer fulano de tal",
		"description":"bla bla",
		"finishDay":"2023-05-12"
	}]
}
``` 

Where cnpj is the manager's identifier and cpf is the employee identifier.
Done that, wait for this answer.

 <img src="https://user-images.githubusercontent.com/63808405/210466626-34833eb6-d802-4ad2-af80-fb78e2a77fa5.png"/></br></br>
 
 The patch request is used because we only update the list of employees that have a list of job and the manager has all this informations, when the manager object is saved again, due to the existing relationship between manager and employee and employee and jobs, a job is automatically created and employee jobs list updated.
 
 ```bash
  isFinished represents whether the job is finished.
  wantDelete represents whether the employee requested the cancellation of the job
  isCanceled represents whether the job is cancelled.
``` 


 We use the follow endpoints to update manager and employee:
 I will update the manager and employee data so that we can use the email service.

updating manager:

```bash
@PUT
manager/update-fields-from/{cnpj}
``` 

```bash
{
	"mname":"Valter Gabriel",
	"memail":"valoira08r@hotmail.com",
	"mcompany":"Dev Cast",
	"mphone":"65121321",
	"password":"1sssssss233333"
}
``` 

updating employee:

```bash
@PUT
employee/update-from/{cnpj}/where-id/{cpf}
``` 

```bash
{
	"ename":"Neymar Jr",
	"eemail":"vgabrielbri@gmail.com",
	"ephone":"12345789541",
	"isActive":1,
	"gender":1,
	"bornDay":"2020-12-31",
	"hireDate":"2000-12-31",
  "password":"as.5asdfasdf56.asdf.65a1s.d6f51a2s.6d5f12.6as5d2v.65sdaf2.6v52sd.6f5v2.6sd51fv.65s1d6d1f26as51df.6a5s1d.f65"
}
``` 

we can also update a job with

```bash
@PUT
job/update/{id}/where-employer/{cpf}/from-manager/{cnpj}

where id = jobId
``` 


```bash
{
	"name":"Pular de aviao",
	"description":"pega o voo",
	"isCanceled":false,
	"finishDay":"2024-12-31",
	"wantDelete":true,
	"isFinished":false
}
``` 


Once this is done, we can use our email service with the following endpoints.

For an employee to request job cancellation to their manager:

```bash
@POST
email/request-delete/{cnpj}/{cpf}/{id}

where id = jobId
``` 


with body:
```bash
{
	"reason":"precisa cancelar pq ta muito dificil"
}
``` 

the manager will receive the following email:</br></br>
<img src="https://user-images.githubusercontent.com/63808405/210467850-6db55b76-a6ee-4fb9-b619-08c2f258a767.png"/></br></br>

And for an employee to request job extend finish date to their manager:

```bash
@POST
email/request-extend-time/{cnpj}/{cpf}/{id}

where id = jobId
``` 


with body:
```bash
{
	"reason":"o prazo é muito curto para realizar o trabalho"
}
``` 

the manager will receive the following email: </br></br>
<img src="https://user-images.githubusercontent.com/63808405/210468015-a903478e-c2d2-4ce5-979e-9f0b53afac8d.png"/></br></br>


<h1>Other application endpoints</h1></br>


<h3>GET</h3></br>

```bash
Request | Response
:-------: | ------:
manager/find-by-cnpj/{cnpj} | find manager by cnpj
employee/find-all-by-manager/{cnpj} | return all manager's employers
employee/find-by-id/{cpf} | find employee by cpf
job/get/all | return all jobs
job/get/all-canceled | return all jobs that are canceled
job/get/all-finished | return all jobs that are finished
job/get/all-to-delete | return all jobs that want to delete
job/get/expires-today | return all jobs that expire on the current day
job/get-by-id{id} | return job by id
```



<h3>DELETE</h3></br>

```bash
Request | Response
:-------: | ------:
job/delete/{id}   | Delete the specified job
employee/delete/{cpf}/from/{cnpj}   | Delete the employee of a specific manager
manager/delete/{cnpj}   | Delete specific manager
```

<h1>All application endpoints</h1></br>

<h3>POST</h3></br>

```bash
Request | Response
:-------: | ------:
manager/sign-up   | create a new manager
email/request-delete/{cnpj}/{cpf}/{id}   | request deletion of a job by email
email/request-extend-time/{cnpj}/{cpf}/{id}   | request the update of a work by email
```

<h3>PATCH</h3></br>

```bash
Request | Response
:-------: | ------:
employee/create-employee   | create a new employee
create-employee   | create a new job
```

<h3>PUT</h3></br>

```bash
Request | Response
:-------: | ------:
manager/update-fields-from/{cnpj}   | update manager
employee/update-from/{cnpj}/where-id/{cpf}   | update employee
job/update/{id}/where-employer/{cpf}/from-manager/{cnpj}   | update job
```

<h3>GET</h3></br>

```bash
Request | Response
:-------: | ------:
manager/find-by-cnpj/{cnpj} | find manager by cnpj
employee/find-all-by-manager/{cnpj} | return all manager's employers
employee/find-by-id/{cpf} | find employee by cpf
job/get/all | return all jobs
job/get/all-canceled | return all jobs that are canceled
job/get/all-finished | return all jobs that are finished
job/get/all-to-delete | return all jobs that want to delete
job/get/expires-today | return all jobs that expire on the current day
job/get-by-id{id} | return job by id
```


<h3>DELETE</h3></br>

```bash
Request | Response
:-------: | ------:
job/delete/{id}   | Delete the specified job
employee/delete/{cpf}/from/{cnpj}   | Delete the employee of a specific manager
manager/delete/{cnpj}   | Delete specific manager
```

<h1>Credits</h1>

---

<a href="https://www.linkedin.com/in/valter-gabriel">
  <img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/63808405/171045850-84caf881-ee10-4782-9016-ea1682c4731d.jpeg" width="100px;" alt=""/>
  <br />
  <sub><b>Valter Gabriel</b></sub></a> <a href="https://www.linkedin.com/in/valter-gabriel" title="Linkedin">🚀</ a>
 
Made by Valter Gabriel 👋🏽 Get in touch!

[![Linkedin Badge](https://img.shields.io/badge/-Gabriel-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/valter-gabriel/ )](https://www.linkedin.com/in/valter-gabriel/)

