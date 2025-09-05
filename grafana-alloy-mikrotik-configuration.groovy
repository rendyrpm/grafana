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

loki.write "default" {
        endpoint {
                url = "http://localhost:3100/loki/api/v1/push"
        }
        external_labels = {}
}