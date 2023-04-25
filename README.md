The Node Registry is a centralized, multi-tenant store of properties of the applications and integration points on the network. The information in the Node Registry can be used for dynamically connecting to applications, runtime rules within integration processes, integration process deployment, load balancing, visualization of complex network topologies, and other use cases.

# Run application

Before start run a <code>mvn validate</code> to install local  dependency <code>lib/CassandraJDBC42.jar</code>. For local development, you can use a *local* profile. This profile requires to have your cassandra running locally.
The *dev* and *qa* profile connects to the given environment accordingly. The *dev* and *qa* profiles require cloud cassandra connection. You can also run it from your local, using
*sshuttle* (see below).

# Steps to run test cases:

1. Ensure Docker engine is running (<code>docker ps</code>)
2. Enable the reuse feature inside <code>~/.testcontainers.properties</code> file in home directory to speed up tests
    1. <code>testcontainers.reuse.enable=true</code>
3. Run all integration tests with <code>mvn -Dmaven.test.skip=false test</code> (head's up, by default tests are
   disabled)
4. Ensure the Docker containers for Cassandra are still running with <code>docker ps</code>

# Routing AWS services to localhost

Required `sshutle`

`sshuttle --dns -r <bastion-user>@<bastion-host:port> 10.0.0.0/8 --ssh-cmd 'ssh -i <bastion-cert>'`
