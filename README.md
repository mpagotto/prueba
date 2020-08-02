# redis-event

Test based on https://medium.com/@sauravomar01/redis-keyspace-notifications-for-expired-keys-f38c18484a89 to listen to redis expiration events

Instructions:
Enable keyspace notifications in local redis for expiration events.

```sh
$ redis-cli config set notify-keyspace-events Ex
OK
```

