package com.nofaultofmine.mgp_p4;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

// Created by TanSiewLan2021

public class EntityManager {

    public final static EntityManager Instance = new EntityManager();
    private LinkedList<EntityBase> entityList = new LinkedList<EntityBase>();
    private SurfaceView view = null;

    public Collidable prev = null;

    private EntityManager()
    {
    }

    public void Init(SurfaceView _view)
    {
        view = _view;
    }

    public void Update(float _dt)
    {
        LinkedList<EntityBase> removalList = new LinkedList<EntityBase>();

        // Update all
        for(int i = 0; i < entityList.size(); ++i)
        {
            // Lets check if is init, initialize if not
            if (!entityList.get(i).IsInit())
            {
                entityList.get(i).Init(view);
            }

            entityList.get(i).Update(_dt);

            // Check if need to clean up
            if (entityList.get(i).IsDone()) {
                // Done! Time to add to the removal list
                removalList.add(entityList.get(i));
            }
        }

        for (EntityBase currEntity : entityList)
        {
            // Lets check if is init, initialize if not
            if (!currEntity.IsInit())
                currEntity.Init(view);

            currEntity.Update(_dt);

            // Check if need to clean up
            if (currEntity.IsDone()) {
                // Done! Time to add to the removal list
                removalList.add(currEntity);
            }
        }

        // Remove all entities that are done
        for (EntityBase currEntity : removalList) {
            entityList.remove(currEntity);
        }
        removalList.clear(); // Clean up of removal list

        // Collision Check
        for (int i = 0; i < entityList.size(); ++i)
        {
            EntityBase currEntity = entityList.get(i);

            if (currEntity instanceof Collidable)
            {
                Collidable first = (Collidable) currEntity;

                for (int j = i+1; j < entityList.size(); ++j)
                {
                    EntityBase otherEntity = entityList.get(j);

                    if (otherEntity instanceof Collidable)
                    {
                        Collidable second = (Collidable) otherEntity;
                        if (first.GetHBTYPE() == Collidable.hitbox_type.HB_BOX && second.GetHBTYPE() == Collidable.hitbox_type.HB_BOX) // if both are boxes
                        {
                            Vector2 firstMax = first.GetMax();
                            Vector2 firstMin = first.GetMin();
                            Vector2 secondMax = second.GetMax();
                            Vector2 secondMin = second.GetMin();
                            if(Collision.BoxToBox(firstMin, firstMax,secondMin,secondMax))
                            {
                                //if(first.GetType() == "PLAYER" || second.GetType() == "PLAYER")
                                //{
                                //    if(first.GetType() == "PLAYER")
                                //    {
                                //        if(first instanceof Smurf)
                                //        {
                                //            Smurf player = (Smurf)first;
                                //            if(player.hasLanded)
                                //            {
                                //                toMove = true;
                                //            }
                                //        }
                                //        prev = second;
                                //    }
                                //    if(second.GetType() == "PLAYER")
                                //    {
                                //        if(second instanceof Smurf)
                                //        {
                                //            Smurf player = (Smurf)first;
                                //            if(player.hasLanded)
                                //            {
                                //                toMove = true;
                                //            }
                                //        }
                                //        prev = first;
                                //    }
                                //}
//
                                first.OnHit(second);
                                second.OnHit(first);
                            }
                        }
                        else if(first.GetHBTYPE() == Collidable.hitbox_type.HB_BOX || second.GetHBTYPE() == Collidable.hitbox_type.HB_BOX) // if one of them has a box hitbox
                        {
                            if(first.GetHBTYPE() == Collidable.hitbox_type.HB_BOX)
                            {
                                Vector2 firstMax = first.GetMax();
                                Vector2 firstMin = first.GetMin();
                                if(Collision.SphereToBox(second.GetPosX(), second.GetPosY(), second.GetRadius(), firstMin, firstMax))
                                {
                                    first.OnHit(second);
                                    second.OnHit(first);
                                }
                            }
                            if(first.GetHBTYPE() == Collidable.hitbox_type.HB_BOX)
                            {
                                Vector2 firstMax = first.GetMax();
                                Vector2 firstMin = first.GetMin();
                                if(Collision.SphereToBox(second.GetPosX(), second.GetPosY(), second.GetRadius(), firstMin, firstMax))
                                {
                                    first.OnHit(second);
                                    second.OnHit(first);
                                }
                            }
                        }
                        else
                        {
                            if (Collision.SphereToSphere(first.GetPosX(), first.GetPosY(), first.GetRadius(), second.GetPosX(), second.GetPosY(), second.GetRadius()))
                            {
                                first.OnHit(second);
                                second.OnHit(first);
                            }
                        }
                    }
                }
            }
            // Check if need to clean up
            if (currEntity.IsDone()) {
                removalList.add(currEntity);
            }
        }

        for (int i = 0; i < entityList.size(); ++i)
        {
            EntityBase x = entityList.get(i);
            if (x instanceof Collidable)
            {
                Collidable curEntity = (Collidable) x;
                if (curEntity.GetType() == "Platform" || curEntity.GetType() == "PLAYER") {
                    curEntity.SetPosition(new Vector2(curEntity.GetPosX(), curEntity.GetPosY()).Plus(new Vector2(0, 2.f)));
                }
                if (curEntity.GetType() == "Platform" && curEntity.GetPosY() > view.getHeight()) {
                    curEntity.SetPosition(new Vector2(curEntity.GetPosX(),0));
                }
            }
        }

        // Remove all entities that are done
        for (EntityBase currEntity : removalList) {
            entityList.remove(currEntity);
        }
        removalList.clear();
    }

    public void Render(Canvas _canvas)
    {
      
        // Use the new "rendering layer" to sort the render order
        Collections.sort(entityList, new Comparator<EntityBase>() {
            @Override
            public int compare(EntityBase o1, EntityBase o2) {
                return o1.GetRenderLayer() - o2.GetRenderLayer();
            }
        });

        for(int i = 0; i <entityList.size(); ++i)
        {
            entityList.get(i).Render(_canvas);
        }
    }

    public void AddEntity(EntityBase _newEntity, EntityBase.ENTITY_TYPE entity_type)
    {
        entityList.add(_newEntity);
    }

    public void Clean()
    {
        entityList.clear();
    }
}


