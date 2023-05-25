package com.mustaphaslimani.geneticalgorithmgui.sma;

import jade.core.AID;

public class AgentFitness implements Comparable{
    private AID aid;
    private int fitness;

    public AgentFitness(AID aid, int fitness){
        this.aid = aid;
        this.fitness = fitness;
    }

    public jade.core.AID getAid() {
        return aid;
    }

    public void setAid(jade.core.AID aid) {
        this.aid = aid;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
    @Override
    public int compareTo(Object o) {
        AgentFitness agentFitness=(AgentFitness) o;
        if (this.fitness>agentFitness.fitness)
            return 1;
        else if(this.fitness<agentFitness.fitness){
            return -1;
        }else
            return 0;
    }
}
