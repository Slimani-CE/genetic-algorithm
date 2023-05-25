package com.mustaphaslimani.geneticalgorithmgui.sma;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class IndividualAgent extends Agent {
    private char genes[]=new char[GAUtils.TARGET_CHROMOSOME.length()];
    private int fitness;
    Random rnd=new Random();
    @Override
    protected void setup() {
        DFAgentDescription dfAgentDescription=new DFAgentDescription();
        dfAgentDescription.setName(getAID());
        ServiceDescription serviceDescription=new ServiceDescription();
        serviceDescription.setType("ga");
        serviceDescription.setName("ga_ma");
        dfAgentDescription.addServices(serviceDescription);
        try {
            DFService.register(this,dfAgentDescription);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        for (int i=0;i<genes.length;i++){
            genes[i]= GAUtils.CHARACTERS.charAt(rnd.nextInt(GAUtils.CHARACTERS.length()));
        }
        //mutation
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMSG = receive();
                if(receivedMSG!=null){
                    switch (receivedMSG.getContent()){
                        case "mutation": mutation();break;
                        case "fitness" : calculateFitness(receivedMSG);break;
                        case "chromosome": sendChromosome(receivedMSG);break;
                        default: changeChromosome(receivedMSG);break;
                    }
                }else {
                    block();
                }
            }
        });
    }

    private void mutation(){
        int index=rnd.nextInt(GAUtils.TARGET_CHROMOSOME.length());
        if (rnd.nextDouble()<GAUtils.MUTATION_RATE){
            genes[index]=GAUtils.CHARACTERS.charAt(rnd.nextInt(GAUtils.CHARACTERS.length()));
        }
    }

    private void calculateFitness(ACLMessage receivedMSG){
        fitness=0;
        for (int i=0;i<GAUtils.TARGET_CHROMOSOME.length();i++) {
            if(genes[i]==GAUtils.TARGET_CHROMOSOME.charAt(i))
                fitness+=1;
        }
        ACLMessage replyMsg=receivedMSG.createReply();
        replyMsg.setContent(String.valueOf(fitness));
        send(replyMsg);
    }
    private void sendChromosome(ACLMessage receivedMSG){
        ACLMessage replyMsg=receivedMSG.createReply();
        replyMsg.setContent(new String(genes));
        send(replyMsg);
    }
    private void  changeChromosome(ACLMessage receivedMSG){
        genes=receivedMSG.getContent().toCharArray();
        mutation();
        calculateFitness(receivedMSG);
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
