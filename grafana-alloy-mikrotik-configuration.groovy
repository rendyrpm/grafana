loki.source.syslog "mikrotik" {
        listener { 
                address        = "0.0.0.0:514"
                protocol       = "udp"
        
        syslog_format = "rfc3164"

        labels = {
                job = "mikrotik",
        }
}

        forward_to = [loki.write.default.receiver]

}

loki.process "mikrotik" {
        forward_to = [loki.write.default.receiver]
        stage.regex {
                expression = "^(?P<timestamp>\\w+\\s+\\d+\\s+\\d+:\\d+:\\d+) (?P<host>\\S+) (?P<topics>[^ ]+) (?P<message>.*)$"
        }

        stage.labels{
                values = {
                        host    = "host",
                        topics  = "topics",
                }
        }

        stage.output {
                source = "message"
        }
}

loki.write "default" {
        endpoint {
                url = "http://localhost:3100/loki/api/v1/push"
        }
        external_labels = {}
}