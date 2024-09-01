# Olhó Repo

## Implemented requirements

 - Load Kotlin repos from Github API;
 - Room database is the source of true, when the content is updated on the database, content is updated on the UI;
 - Pagination on the server with a mediator, since could have an lot of repos to display;
 - Details page, with some details
 - Retrofit2
 - Interceptors
 - Hilt 
 - Compose
 - Offline support since the content is stored on the database (we could improve and cached all the images, but we can use for now the coil cache)
 
 ## Setup
 The github auth token should be stored on the system envs, to be used on the Retrofit auth interceptor: 
UNIX:  `export GITHUB_TOKEN="your_personal_access_token_here"`
WINDOWS:  `set GITHUB_TOKEN="your_personal_access_token_here"`

This was to prevent having sensitive data on the source code, and we could set the token on build time. We could also used the gradle.properties. 
