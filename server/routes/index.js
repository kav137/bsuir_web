import express from 'express';
import apiRouter from './api';
import loginRouter from './login';
import logoutRouter from './logout';
import homeRouter from './home';
import welcomeRouter from './welcome';

let secureRouter = express.Router();
let publicRouter = express.Router();

publicRouter.use(loginRouter);
publicRouter.use(logoutRouter);
publicRouter.use(welcomeRouter);

secureRouter.use(homeRouter);
secureRouter.use('/api', apiRouter);

export { secureRouter, publicRouter };