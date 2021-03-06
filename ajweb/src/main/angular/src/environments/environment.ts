// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  API_URL: 'http://localhost:8080/services/',
  LINKED_IN_URL: 'https://www.linkedin.com/oauth/v2/authorization',
  LINKED_IN_CLIENT_ID: '78zmxju74ey7jh',
  LINKED_IN_SCOPE: 'r_liteprofile r_emailaddress w_member_social',
  GOOGLE_URL: 'https://accounts.google.com/o/oauth2/v2/auth',
  GOOGLE_CLIENT_ID: '336119533592-n24lvu4trdacd3b0iaj7b6c9ge9d9kb6.apps.googleusercontent.com',
  GOOGLE_SCOPE: 'openid email profile'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
