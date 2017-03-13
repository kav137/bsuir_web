# bsuir_web
web project

API
*all routes expect /login, /home and /logout require session cookie which would be set by the server after successfull login

/home
	>>html form
	type: GET

/login 
	type: POST 
	x-www-form-urlencoded fields: 'username', 'password'
	return: {token: value}
	if error: sends status 401 and 'Unauthorized' text message

/logout
	>> logout and redirect to /welcome
	type: GET

/welcome
	type: GET
	html view

/api/photos/download/:photoId
	type: GET
	params: photoId (can be acessed via /list (_id property) or after uploading)
	return: <Buffer> of binary image data. content-Type header is set to 'image/png' or 'image/jpg'

/api/photos/list
	>> get a list of photos of current user
	type: GET
	return: {"result":[
		{
			"id":"58c5da2fd966b2291cee3108",  ~~ id of photo (see /download)
			"sizeKb":122.125,
			"name":"ZIhqeIICY_8.jpg",
			"description":"hello there",
			"date":"2017-03-12T23:30:55.600Z",
			"userId":"58acbaa0aae9491150b4c4e4"  ~~ id of current user
		}, 
		...
	]}

/api/photos/upload
	>> uploads a user photo with description
	type: POST,
	form enctype: 'multipart/form-data'
	input1: type='file' name='photo'
	input2: name='description' type='text'
	returns : {success: true, photoId: "58c5da2fd966b2291cee3108"}

/api/users/create
	>> maybe later =)
/api/users/delete
/api/users/edit
/api/users/index
/api/users/read