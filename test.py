import os
import subprocess
import signal
import socket
import time
import random
import traceback

num_node = 10
start_port = 6001
node_list = []
output_fds = []
err_fds = []
test_pro = None
host_name = socket.gethostname() 
host_ip = socket.gethostbyname(host_name) 

def test(num_fail=0):
    with open("/tmp/DHTNode/addr_list", 'w+') as addr_file:
        for i in range(num_node):
            addr_file.write("{!s}:{!s}\n".format(host_ip, start_port + i))

    for i in range(num_node):
        output_fds.append(open("./node_{}_stdout".format(i + start_port), 'w+'))
        err_fds.append(open("./node_{}_stderr".format(i + start_port), 'w+'))

    node_list.append(subprocess.Popen('./build/install/DHT/bin/Chord ' + str(start_port),\
        stdout=output_fds[i], stderr=err_fds[i], shell=True, preexec_fn=os.setsid))

    for i in range(1, num_node):
        time.sleep(1)
        cmd = './build/install/DHT/bin/Chord {!s} {!s} {!s}'.format(i + start_port, host_ip, start_port)
        node_list.append(subprocess.Popen(cmd, stdout=output_fds[i], stderr=err_fds[i], \
            shell=True, preexec_fn=os.setsid))

    output_fds.append(open("./test_stdout", 'w+'))
    err_fds.append(open("./test_stderr", 'w+'))
    time.sleep(1)
    test_pro = subprocess.Popen('./build/install/DHT/bin/Bench', stdout=output_fds[num_node], stderr=err_fds[num_node], \
            shell=True, preexec_fn=os.setsid)
    test_pro.wait()
    output_fds[num_node].close()
    err_fds[num_node].close()
    output_fds[num_node] = open("./test_stdout", 'a')
    err_fds[num_node] = open("./test_stderr", 'a')
    selected = random.sample(range(num_node), num_fail)
    for i in selected:
        os.killpg(os.getpgid(node_list[i].pid), signal.SIGTERM)
        # time.sleep(0.5)

    with open("/tmp/DHTNode/addr_list", 'w+') as addr_file:
        for i in range(num_node):
            if i not in selected:
                addr_file.write("{!s}:{!s}\n".format(host_ip, start_port + i))
    # time.sleep(0.5)
    test_pro = subprocess.Popen('./build/install/DHT/bin/Bench', stdout=output_fds[num_node], stderr=err_fds[num_node], \
            shell=True, preexec_fn=os.setsid)
    test_pro.wait()

if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument('--num_fail', type=int, default=0)
    args = parser.parse_args()
    try:
        test(args.num_fail)
    except Exception as e:
        traceback.print_exc()
    finally:
        for node in node_list:
            if node is not None:
                os.killpg(os.getpgid(node.pid), signal.SIGTERM)
        if test_pro is not None:
            os.killpg(os.getpgid(test_pro.pid), signal.SIGTERM)

