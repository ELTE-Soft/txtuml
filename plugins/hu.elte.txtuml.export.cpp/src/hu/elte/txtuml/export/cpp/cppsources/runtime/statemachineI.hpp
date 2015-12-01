#ifndef STATEMACHINEI_HPP_INCLUDED
#define STATEMACHINEI_HPP_INCLUDED

#include <memory>
#include <mutex>
#include <atomic>

#include "runtimetypes.hpp"

class RuntimeI;
class StateMachineThreadPool;

class StateMachineI
{
public:
  virtual void processEventVirtual()=0;
  virtual void startSM()=0;


  void runSM();
  void send(EventPtr e_);
  EventPtr getNextMessage(){return _messageQueue->front();}
  void deleteNextMessage(){_messageQueue->pop_front();}
  bool emptyMessageQueue(){return _messageQueue->empty();}
  void setPool(StateMachineThreadPool* pool_){_pool=pool_;}
  void setRuntime(RuntimeI* runtime_){_runtime=runtime_;}
  void setMessageQueue(std::shared_ptr<MessageQueueType> messageQueue_){_messageQueue=messageQueue_;}
  void setPooled(bool value_=true){_inPool=value_;}
  bool isInPool(){return _inPool;}
  virtual ~StateMachineI(){}
protected:
  StateMachineI(std::shared_ptr<MessageQueueType> messageQueue_=std::shared_ptr<MessageQueueType>(new MessageQueueType()));
  RuntimeI* _runtime;////safe because: controlled by the runtime, but we can not set it in the constructor
private:
  void handlePool();

  std::shared_ptr<MessageQueueType> _messageQueue;
  StateMachineThreadPool* _pool;//safe because: controlled by the runtime, but we can not set it in the constructor
  std::mutex _mutex;
  std::atomic_bool _inPool;
};

#endif // STATEMACHINEI_HPP_INCLUDED
