/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworld;

/**
 *
 * @author Anatoli Grishenko <Anatoli.Grishenko@gmail.com>
 */
public class AgentLinearProfiler extends AgentIncrementalProfiler {


    @Override
    public void takeDown() {
        if (getMyNetworkProfiler().isActive()) {
            getMyNetworkProfiler().close();
            getMyNetworkProfiler().saveAll("profiler/"+getLocalName()+"-NETWORK.tsv");
        }

        super.takeDown();
    }

}
