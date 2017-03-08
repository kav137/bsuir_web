import mongoose from 'mongoose';

let userSchema = mongoose.Schema( {
	id: Number,
	username: String,
	password: String
});

userSchema.methods.validatePassword = ( password ) => {
	return ( this.password === password );
};

userSchema.methods.getToken = () => {
	return Promise.resolve( this._id );
};

let User = mongoose.model( 'User', userSchema );

let getToken = ( user ) => {
	return user.getToken();
};

let createUser = ( username, password ) => {
	return new Promise(( resolve, reject ) => {
		User.find( { username }, ( err, res ) => {
			if ( err ) {
				reject();
			}
			if ( res && res.length ) {
				console.log( `user ${username} already exists` );
				reject();
			}
			else {
				resolve();
			}
		});
	}).then(() => {
		let newUser = new User( { username, password });
		return new Promise(( resolve, reject ) => {
			newUser.save(( err, result ) => {
				if ( err ) {
					console.log( `error during saving item ${newUser.username}` );
					reject( err );
				}
				console.log( `successfully saved ${newUser.username}` );
				resolve( result );
			});
		});
	});
};

let insertInitialData = () => {
	let users = [
		{ username: 'art', password: 'web' },
		{ username: 'vlad', password: 'web' },
		{ username: 'nick', password: 'web' }
	];

	return Promise.all( users.map(( user ) => {
		return createUser( user.username, user.password );
	}) );
};

let deleteAll = () => {
	return new Promise(( resolve, reject ) => {
		User.remove( {}, ( err ) => {
			if ( err ) {
				console.log( 'there was a error during removing. failed' );
				reject();
			}
			console.log( 'all users were removed' );
			resolve();
		});
	});
};

let findOne = ( conditions ) => {
	console.log( 'searching' );
	console.log( conditions );
	return new Promise(( resolve, reject ) => {
		User.findOne( conditions, ( err, result ) => {
			if ( err ) {
				reject( new Error( 'error during search' ) );
			}
			resolve( result );
		});
	});
};

let getAllData = () => {
	return new Promise(( resolve, reject ) => {
		User.find( {}, ( err, result ) => {
			if ( err ) {
				console.log( 'there was a error during data retrieving' );
				reject();
			}
			resolve( result );
		});
	});
};

export default {
	User,
	findOne,
	insertInitialData,
	deleteAll,
	createUser,
	getAllData,
	getToken
};

