package com.mustaphaslimani.geneticalgorithmgui.sma;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainAgentGA extends Agent {
    List<AgentFitness> agentsFitness = new ArrayList<>();
    Random random = new Random();

    @Override
    protected void setup() {
        DFAgentDescription dfAgentDescription = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("GA");
        dfAgentDescription.addServices(serviceDescription);
        try{
            DFAgentDescription[] agentDescriptions = DFService.search(this, dfAgentDescription);
            for(DFAgentDescription dfAD : agentDescriptions){
                agentsFitness.add(new AgentFitness(dfAD.getName(), 0));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        calculateFitness();
        SequentialBehaviour sequentialBehaviour = new SequentialBehaviour();
        sequentialBehaviour.addSubBehaviour(new Behaviour() {
            int cpt = 0;
            @Override
            public void action() {
                ACLMessage receivedMessage = receive();
                if(receivedMessage != null){
                    cpt++;
                    System.out.println(cpt);
                    int fitness = Integer.parseInt(receivedMessage.getContent());
                    AID sender = receivedMessage.getSender();
                    setAgentFitness(sender, fitness);
                    if(cpt == GAUtils.POPULATION_SIZE){
                        Collections.sort(agentsFitness, Collections.reverseOrder());
                        showPopulation();
                    }
                }else {
                    block();
                }
            }
            @Override
            public boolean done() {
                return cpt == GAUtils.POPULATION_SIZE;
            }
        });
        sequentialBehaviour.addSubBehaviour(new Behaviour() {
            int it = 0;
            AgentFitness agents1;
            AgentFitness agents2;

            @Override
            public void action() {
                selection();
                crossover();
                Collections.sort(agentsFitness, Collections.reverseOrder());
                sendMessage(agentsFitness.get(0).getAid(), "chromosome", ACLMessage.REQUEST);
                ACLMessage aclMessage = blockingReceive();
                System.out.println(it + " : " + aclMessage.getContent() + " : " + agentsFitness.get(0).getFitness());
                it++;
            }

            private void selection() {
                agents1 = agentsFitness.get(0);
                agents2 = agentsFitness.get(1);
                sendMessage(agents1.getAid(), "chromosome", ACLMessage.REQUEST);
                sendMessage(agents2.getAid(), "chromosome", ACLMessage.REQUEST);
            }

            private void crossover(){
                ACLMessage aclMessage1 = blockingReceive();
                ACLMessage aclMessage2 = blockingReceive();

                int crossoverPoint = random.nextInt(GAUtils.TARGET_CHROMOSOME.length()-2);
                crossoverPoint += 1;
                char[] chromParent1 = aclMessage1.getContent().toCharArray();
                char[] chromParent2 = aclMessage2.getContent().toCharArray();
                char[] chromOffspring1 = new char[GAUtils.TARGET_CHROMOSOME.length()];
                char[] chromOffspring2 = new char[GAUtils.TARGET_CHROMOSOME.length()];

                for(int i = 0; i < GAUtils.TARGET_CHROMOSOME.length(); i++){
                    if(i < crossoverPoint){
                        chromOffspring1[i] = chromParent1[i];
                        chromOffspring2[i] = chromParent2[i];
                    }else {
                        chromOffspring1[i] = chromParent2[i];
                        chromOffspring2[i] = chromParent1[i];
                    }
                }

                int fitness = 0;
                for(int i = 0; i < GAUtils.TARGET_CHROMOSOME.length(); i++){
                    if(chromOffspring1[i] == GAUtils.TARGET_CHROMOSOME.charAt(i))
                        fitness += 1;
                }
                agentsFitness.get(GAUtils.POPULATION_SIZE-2).setFitness(fitness);

                sendMessage(agentsFitness.get(GAUtils.POPULATION_SIZE-2).getAid(), new String(chromOffspring1), ACLMessage.INFORM);
                sendMessage(agentsFitness.get(GAUtils.POPULATION_SIZE-1).getAid(), new String(chromOffspring2), ACLMessage.INFORM);

                ACLMessage receivedMessage1 = blockingReceive();
                ACLMessage receivedMessage2 = blockingReceive();

                setAgentFitness(receivedMessage1.getSender(), Integer.parseInt(receivedMessage1.getContent()));
                setAgentFitness(receivedMessage2.getSender(), Integer.parseInt(receivedMessage2.getContent()));
            }

            @Override
            public boolean done() {
                return it == GAUtils.MAX_ITERATIONS || agentsFitness.get(0).getFitness() == GAUtils.TARGET_CHROMOSOME.length();
            }
        });
        addBehaviour(sequentialBehaviour);
    }
    private void calculateFitness(){
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);

        for(AgentFitness agentFitness : agentsFitness){
            message.addReceiver(agentFitness.getAid());
        }
        message.setContent("fitness");
        send(message);
    }
    private void setAgentFitness(AID aid, int fitness){
        for(int i = 0; i < GAUtils.POPULATION_SIZE; i++){
            if(agentsFitness.get(i).getAid().equals(aid)){
                agentsFitness.get(i).setFitness(fitness);
                break;
            }
        }
    }
    private void sendMessage(AID aid, String content, int performative){
        ACLMessage message = new ACLMessage(performative);
        message.addReceiver(aid);
        message.setContent(content);
        send(message);
    }
    private void showPopulation(){
        System.out.println("Population : ");
        for(AgentFitness agentFitness : agentsFitness){
            System.out.println(agentFitness.getAid().getName() + " : " + agentFitness.getFitness());
        }
    }
}
