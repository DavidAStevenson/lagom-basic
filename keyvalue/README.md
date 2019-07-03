# keyvalue

This is a very very simple Lagom project to confirm some basic features.

Kick off the Lagom development environment:
```
sbt runAll
```

After it's up and running, set a Value for Key - for example, LastName as the value for a key of FirstName:
```
curl -s -X POST -d '{ "value": "LastName" }' -i -H "Content-Type: application/json" http://localhost:9000/api/key/FirstName
```

